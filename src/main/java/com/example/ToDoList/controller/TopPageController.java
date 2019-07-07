package com.example.ToDoList.controller;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.example.ToDoList.entities.ToDoItem;
import com.example.ToDoList.service.ToDoItemService;

/*
 * toDoList情報
 */
@Controller
public class TopPageController {

  @Autowired
  ToDoItemService toDoItemService;

  // @Autowired
  // protected ResourceLoader resourceLoader;

  @RequestMapping(value="/", method=RequestMethod.GET)
  public String displayList(Model model){
    // String pathAndFile = "../css/topPage.css";
    // Resource resource = resourceLoader.getResource("classpath:" + pathAndFile);
    // model.addAttribute("resourceFileName",resource.getFilename());
    List<ToDoItem> toDoItems = this.toDoItemService.findAll();
    model.addAttribute("toDoItems", toDoItems);
    return "topPage";
  }

  @RequestMapping(value="/new", method=RequestMethod.POST)
  public String newItem(ToDoItem item) {
      item.setCreated_at(settingCreated_at());
      this.toDoItemService.save(item);
      return "redirect:/";
  }

  //編集ボタンクリック時
  @RequestMapping(value="/edit", method=RequestMethod.POST)
  public String editCard(@RequestParam("id") Integer id, Model model){
    ToDoItem item = this.toDoItemService.findById(id);
    model.addAttribute("toDoItem", item);
    return "editPage";
  }

  public String settingCreated_at(){
    //カレンダークラスのオブジェクトを生成する
    Calendar cl = Calendar.getInstance();
    //フォーマットを指定する
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    //フォーマットをフォーマットを変更する
    //sdf.applyPattern("yyyy年MM月dd日");
    return sdf.format(cl.getTime()).toString(); 
  }
}