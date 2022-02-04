package studentmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import studentmanagement.model.SearchBean;
import studentmanagement.model.StudentBean;
import studentmanagement.persistant.dto.ClassResponseDto;
import studentmanagement.persistant.dto.StudentRequestDto;
import studentmanagement.persistant.dto.StudentResponseDto;
import studentmanagement.service.ClassServiceImpl;
import studentmanagement.service.StudentServiceImpl;

@Controller
public class StudentController {

	@Autowired
	private StudentServiceImpl sDAO;

	@Autowired
	private ClassServiceImpl cDAO;

	@RequestMapping(value = "/setupStudentSearch", method = RequestMethod.GET)
	public ModelAndView setupStudentSearch(@ModelAttribute("Success") String Success, ModelMap model) {
		model.addAttribute("Success", Success);
		return new ModelAndView("BUD001", "sStuBean", new SearchBean());
	}

	@RequestMapping(value = "/searchStudent", method = RequestMethod.GET)
	public String searchStudent(@ModelAttribute("sStuBean") SearchBean searchBean, ModelMap model) {

		List<StudentResponseDto> studentList = new ArrayList<>();
		StudentRequestDto sDTO = new StudentRequestDto();

		sDTO.setStudentId(searchBean.getStuId());
		sDTO.setStudentName(searchBean.getStuName());
		sDTO.setClassName(searchBean.getClassName());

		if (!sDTO.getStudentId().equals("") || !sDTO.getStudentName().equals("") || !sDTO.getClassName().equals("")) {
			studentList = sDAO.selectOne(sDTO);
		} else {
			studentList = sDAO.selectAll();
		}

		if (studentList.size() == 0) {
			model.addAttribute("Error", "No Student Found !!!");
		} else {
			model.addAttribute("studentList", studentList);
			model.addAttribute("Success", "Search done Successfully");
		}
		return "BUD001";
	}

	@RequestMapping(value = "/setupStudentAdd", method = RequestMethod.GET)
	public ModelAndView setupAddStudent() {

		return new ModelAndView("BUD002", "stuBean", new StudentBean());
	}

	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public String addStudent(@ModelAttribute("stuBean") @Validated StudentBean studentBean, BindingResult br,
			ModelMap model) {

		boolean condition1 = br.hasErrors();
		boolean condition2 = (studentBean.getYear().equals("Year") || studentBean.getMonth().equals("Month")
				|| studentBean.getDay().equals("Day"));
		if (condition1) {
			return "BUD002";
		} else if (condition2) {
			model.addAttribute("ErrorDate", "RegisterDate can't be blank!");
			return "BUD002";
		} else {

			StudentRequestDto sDTO = new StudentRequestDto();
			sDTO.setStudentId(studentBean.getId());

			List<StudentResponseDto> checkStudentList = sDAO.selectOne(sDTO);

			if (checkStudentList.size() != 0) {
				model.addAttribute("Error", "Student ID has been already exist ... Choose another Student ID");
			} else {
				sDTO.setStudentName(studentBean.getName());
				sDTO.setClassName(studentBean.getClassName());
				sDTO.setRegisterDate(studentBean.getYear() + "-" + studentBean.getMonth() + "-" + studentBean.getDay());
				sDTO.setStatus(studentBean.getStatus());
				int i = sDAO.insert(sDTO);
				if (i > 0) {
					model.addAttribute("Remain", studentBean);
					model.addAttribute("Success", "Student registered Successfully");
				}
			}
		}
		return "BUD002";
	}

	@RequestMapping(value = "/setupUpdateStudent/{studentId}", method = RequestMethod.GET)
	public ModelAndView setupUpdateStudent(@PathVariable String studentId, ModelMap model) {

		StudentRequestDto dto = new StudentRequestDto();
		dto.setStudentId(studentId);

		List<StudentResponseDto> list = sDAO.selectOne(dto);
		StudentBean studentBean = new StudentBean();
		for (StudentResponseDto res : list) {
			studentBean.setId(res.getStudentId());
			studentBean.setName(res.getStudentName());
			studentBean.setClassName(res.getClassName());
			studentBean.setStatus(res.getStatus());
			String[] date = res.getRegisterDate().toString().split("-");

			studentBean.setYear(date[0]);
			studentBean.setMonth(date[1]);
			studentBean.setDay(date[2]);
		}
		model.addAttribute("upRemain", studentBean);
		return new ModelAndView("BUD002-01", "stuBean", studentBean);
	}

	@RequestMapping(value = "/studentUpdate", method = RequestMethod.POST)
	public String studentUpdate(@ModelAttribute("stuBean") @Validated StudentBean studentBean, BindingResult br,
			ModelMap model) {

		boolean condition1 = br.hasErrors();
		boolean condition2 = (studentBean.getYear().equals("Year") || studentBean.getMonth().equals("Month")
				|| studentBean.getDay().equals("Day"));
		if (condition1) {
			return "BUD002-01";
		} else if (condition2) {

			model.addAttribute("ErrorDate", "RegisterDate can't be blank!");
			return "BUD002-01";

		} else {

			StudentRequestDto sDTO = new StudentRequestDto();
			sDTO.setStudentId(studentBean.getId());
			sDTO.setStudentName(studentBean.getName());
			sDTO.setClassName(studentBean.getClassName());
			sDTO.setRegisterDate(studentBean.getYear() + "-" + studentBean.getMonth() + "-" + studentBean.getDay());
			sDTO.setStatus(studentBean.getStatus());

			int i = sDAO.update(sDTO);
			if (i > 0) {
				model.addAttribute("Success", "Student successfully updated");
			}
		}
		return "BUD002-01";
	}

	@RequestMapping(value = "/studentDelete/{id}", method = RequestMethod.GET)
	public String studentDelete(@PathVariable String id, ModelMap model, RedirectAttributes redir) {
		StudentRequestDto sDTO = new StudentRequestDto();
		sDTO.setStudentId(id);
		int i = sDAO.delete(sDTO);
		if (i > 0) {
			redir.addAttribute("Success", "Deleted " + sDTO.getStudentId() + " Successfully");
		}
		return "redirect:/setupStudentSearch/";
	}

	@ModelAttribute("cList")
	public List<ClassResponseDto> classList() {

		List<ClassResponseDto> classList = cDAO.selectAll();
		return classList;
	}
}
