package br.com.bilotta.ordemServicosAPI.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.bilotta.ordemServicosAPI.domain.exception.EntidadeNaoEncontradaException;
import br.com.bilotta.ordemServicosAPI.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(NegocioException ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		
		var problem = new Problem();
		problem.setStatus(status.value());
		problem.setTitulo(ex.getMessage());
		problem.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		
		var problem = new Problem();
		problem.setStatus(status.value());
		problem.setTitulo(ex.getMessage());
		problem.setDataHora(OffsetDateTime.now());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var campos = new ArrayList<Problem.Campo>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new Problem.Campo(nome, mensagem));
		}
		
		var problem = new Problem();
		problem.setStatus(status.value());
		problem.setTitulo("Um ou mais campos estão inválidos. Realize corretamente o preenchimento e tente novamente.");
		problem.setDataHora(OffsetDateTime.now());
		problem.setCampos(campos);
		
		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}
}
