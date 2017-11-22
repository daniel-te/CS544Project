package mum.cs544.project.controller;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mum.cs544.project.entity.Appointment;
import mum.cs544.project.entity.Person;
import mum.cs544.project.entity.Session;
import mum.cs544.project.service.IAppointmentService;
import mum.cs544.project.service.IPersonService;
import mum.cs544.project.service.ISessionService;
import mum.cs544.project.util.SecurityUtil;

@Controller
public class AppointmentController {
	
	@Autowired
	ISessionService sessionService;
	
	@Autowired
	IAppointmentService appointmentService;
	
	@Autowired
	IPersonService personService;

	@RequestMapping(value = "/appointment/create", method = RequestMethod.GET)
	public String getAllSessions(Model model) {
		List<Session> allAvailableSessions = sessionService.getAllFutureSessions();
		long personid = personService.findByUsername(SecurityUtil.getLoggedInUserName()).getId();
		allAvailableSessions = allAvailableSessions.stream()
				.filter(session -> (session.getCapacity() > session.getAttendees().size()))
				.filter(session -> (session.getAttendees().stream().filter(appt -> appt.getPerson().getId() == personid).count() == 0))
				.collect(Collectors.toList());
		model.addAttribute("sessions", allAvailableSessions);
		return "createappointment";
	}

	@RequestMapping(value = "/appointment/list", method = RequestMethod.GET)
	public String getAllAppointments(Model model) {
		model.addAttribute("appointments",
				personService.findByUsername(SecurityUtil.getLoggedInUserName()).getAppointments());
		return "listappointment";
	}

	@RequestMapping(value = "/appointment/save", method = RequestMethod.GET)
	public String createAppointment(@RequestParam(value = "sessionID") long sessionid, Model model) {
		String view = "redirect:/appointment/list";
		try {
			Session session = sessionService.getSessionById(sessionid);
			Person person = personService.findByUsername(SecurityUtil.getLoggedInUserName());
			Appointment appt = new Appointment(session, person, person, new Date());
			appointmentService.createAppointment(appt);
		} catch (Exception ex) {
			return "redirect:/appointment/create";
		}
		return view;
	}
	
	@RequestMapping(value = "/appointment/delete", method = RequestMethod.GET)
	public String deleteAppointment(@RequestParam(value = "apptID") long appointmentid, Model model) {
		String view = "redirect:/appointment/list";
		try {
			appointmentService.deleteAppointment(appointmentid);
		} catch (Exception ex) {
			return "redirect:/appointment/list";
		}
		return view;
	}
	
	@RequestMapping(value="signupAppointment", method=RequestMethod.GET)
	public String signupAppointment(Model model) {
//	model.addAttribute("sessions", sessionService.getSessionById(sessionId));
	return "appointmentAdmin";
	}
	
	@RequestMapping(value = "/appointmentDelete", method = RequestMethod.GET)
	public String deleteAppointments(Model model) {
		model.addAttribute("appointments", appointmentService.getAllAppointments());// flash
		// attribute
		return "appointmentDelete";
	}
	
	@RequestMapping(value = "/appointmentManage", method = RequestMethod.GET)
	public String ManageAppointments(Model model) {
		model.addAttribute("sessions", sessionService.getAllFutureSessions());
		model.addAttribute("persons", personService.getAllPerson());
		return "appointmentManage";
	}
	
	@RequestMapping(value = "apptRegisterCustomer/{sessionid}", params="person", method = RequestMethod.POST)
	public String AppointmentAddCustomer(@RequestParam String person,@PathVariable long sessionid, Model model) {
		Session session = sessionService.getSessionById(sessionid);
		Person person1 = personService.findByUsername(person);
		Person creator = personService.findByUsername(SecurityUtil.getLoggedInUserName());
		Appointment appt = new Appointment(session, person1, creator, new Date());
		appointmentService.createAppointment(appt);
		
		model.addAttribute("sessions", sessionService.getAllSessions());
		return "redirect:/appointmentManage";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String adminDeleteAppointment(@RequestParam(value = "apptID") long appointmentid, Model model) {
		String view = "redirect:/appointmentDelete";
		try {
			appointmentService.deleteAppointment(appointmentid);
		} catch (Exception ex) {
			return "redirect:/appointmentDelete";
		}
		return view;
	}
}
