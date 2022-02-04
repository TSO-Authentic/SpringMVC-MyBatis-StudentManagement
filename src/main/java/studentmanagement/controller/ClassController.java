package studentmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import studentmanagement.model.ClassBean;
import studentmanagement.persistant.dto.ClassRequestDto;
import studentmanagement.persistant.dto.ClassResponseDto;
import studentmanagement.service.ClassServiceImpl;

@Controller
public class ClassController {

	@Autowired
	private ClassServiceImpl cDAO;
	
	@RequestMapping(value = "/setupClass", method = RequestMethod.GET)
	public ModelAndView setupClass() {
		return new ModelAndView("BUD003", "cBean" , new ClassBean());
	}
	
	@RequestMapping(value="/addClass", method = RequestMethod.POST)
	public String addClass(@ModelAttribute("cBean") @Validated ClassBean classBean, BindingResult br,ModelMap model) {
		
		if(br.hasErrors()) {
			return "BUD003";
		}
		
		ClassRequestDto cDTO = new ClassRequestDto();
		cDTO.setId(classBean.getId());
		
		List<ClassResponseDto> checkClassList = cDAO.selectOne(cDTO);
		
		if(checkClassList.size() != 0) {
			model.addAttribute("Error", "Class ID has been already used.... Choose another class ID");
		}else {
			cDTO.setName(classBean.getName());
			int i = cDAO.insert(cDTO);
			
			if( i > 0) {
				model.addAttribute("Success", "Class registered Successfully");
			}else {
				model.addAttribute("Error", "Class registered fail !!!");
			}
		}
		return "BUD003";
	}
	
}
