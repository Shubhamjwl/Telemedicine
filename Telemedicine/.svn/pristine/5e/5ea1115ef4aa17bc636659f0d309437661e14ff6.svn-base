package com.nsdl.otpManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.otpManager.entity.EmailSmsTemplateDtl;

public interface TemplateRepository extends JpaRepository<EmailSmsTemplateDtl, Long> {
	EmailSmsTemplateDtl findByTemplateNameIgnoreCase(String templateName);

}
