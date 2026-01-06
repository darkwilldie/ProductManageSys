package com.qrliu.ProductManageSys.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model, HttpServletRequest request) {
        model.addAttribute("error", "发生错误");
        model.addAttribute("message", ex.getMessage() != null ? ex.getMessage() : "未知错误");
        model.addAttribute("status", 500);
        model.addAttribute("path", request.getRequestURI());
        return "error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException ex, Model model, HttpServletRequest request) {
        model.addAttribute("error", "页面未找到");
        model.addAttribute("message", "请求的页面不存在");
        model.addAttribute("status", 404);
        model.addAttribute("path", request.getRequestURI());
        return "error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model, HttpServletRequest request) {
        model.addAttribute("error", "运行时错误");
        model.addAttribute("message", ex.getMessage() != null ? ex.getMessage() : "运行时发生错误");
        model.addAttribute("status", 500);
        model.addAttribute("path", request.getRequestURI());
        return "error";
    }
}
