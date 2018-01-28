package com.project.javaportfolio.controllers;
// import com.project.javaportfolio.services.*;
import com.project.javaportfolio.models.*;
import com.project.javaportfolio.repositories.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Overflow{
	private AnswerRepo answerRepo;
	private QuestionRepo questionRepo;
	private TagRepo tagRepo;
	public Overflow(AnswerRepo answerRepo, QuestionRepo questionRepo, TagRepo tagRepo){
		this.answerRepo = answerRepo;
		this.questionRepo = questionRepo;
		this.tagRepo = tagRepo;
	}
	
	@RequestMapping("/overflow")
	public String overflowIndex(Model model){
		model.addAttribute("questions", questionRepo.findAll());
		return "ofDashboard";
	}

	@RequestMapping("/overflow/questions/new")
	public String questionForm(@ModelAttribute("question") Question question){
		return "newquestion";
	}

	@PostMapping("/overflow/questions/new/submit")
	public String createQuestion(Model model, @RequestParam(value="tag") String tag, @RequestParam(value="body") String body, RedirectAttributes err){
		if (body.isEmpty() && tag.isEmpty()){
			err.addFlashAttribute("errors0", "Question and tags are required.");
			model.addAttribute("errors0", err.getFlashAttributes());
			return "redirect:/overflow/questions/new";
		}
		if (body.isEmpty()){
			err.addFlashAttribute("errors1", "Question is required.");
			model.addAttribute("errors1", err.getFlashAttributes());
			return "redirect:/overflow/questions/new";
		} else if (tag.isEmpty()){
			err.addFlashAttribute("errors2", "At least one tag is required.");
			model.addAttribute("errors2", err.getFlashAttributes());
			return "redirect:/overflow/questions/new";
		} else {
			List<Tag> currentTags = new ArrayList<Tag>();
			tag = tag.replaceAll(",+", " ");
			tag = tag.replaceAll("[.]+", " ");
			tag = tag.replaceAll(" +", " ");
			List<String> allTags = Arrays.asList(tag.split("(, |[.] |,|[.]| )"));
			allTags.removeAll(Arrays.asList(new String[]{"", " ", null}));
			if (allTags.size() < 4){
				for (int i = 0; i < allTags.size(); i++){
					List<Tag> current = tagRepo.findByNameContaining(allTags.get(i));
					if (!current.isEmpty()){
						currentTags.add(current.get(0));
					} else {
						Tag tags = new Tag(allTags.get(i));
						Tag newTag = tagRepo.save(tags);
						currentTags.add(newTag);
					}
				}
				Question newQuestion = new Question(body, currentTags);
				questionRepo.save(newQuestion);
				return "redirect:/overflow";
			} else {
				err.addFlashAttribute("errors3", "No more than three tags");
				model.addAttribute("errors3", err.getFlashAttributes());
				return "redirect:/overflow/questions/new";
			}
		}
	}

	@RequestMapping("/overflow/questions/{id}")
	public String showQuestion(Model model, @PathVariable("id") Long id){
		model.addAttribute("question", questionRepo.findById(id));
		model.addAttribute("answers", answerRepo.findByQuestion(questionRepo.findById(id)));
		return "oneQuestion";
	}

	@PostMapping("/overflow/answer/new/{id}")
	public String newAnswer(Model model, @PathVariable("id") Long id, @RequestParam("body") String body,
			RedirectAttributes err){
		Question question = questionRepo.findById(id);
		if (body.isEmpty()){
			err.addFlashAttribute("answerError", "Answer text is required.");
			model.addAttribute("answerError", err.getFlashAttributes());
			return "redirect:/overflow/questions/{id}";
		}
		Answer newAnswer = new Answer(body, question);
		answerRepo.save(newAnswer);
		return "redirect:/overflow/questions/{id}";
	}

}