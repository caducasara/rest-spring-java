package br.com.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.BookController;
import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.BookMapper;
import br.com.erudio.mapper.PersonMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;

@Service
public class BookServices {
	
	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	BookRepository repository;
	
	public BookVO findById(Long id) {
		
		logger.info("Finding one Book!");
		
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		BookVO vo =  BookMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel());
		
		return vo;
	}
	
	public List<BookVO> findAll() {
		logger.info("Finding all books!");
		
		List<BookVO> books =  BookMapper.parseListObject(repository.findAll(), BookVO.class);
		books
			.stream()
				.forEach(p -> p.add(linkTo(methodOn(BookController.class).getBookById(p.getKey())).withSelfRel()));
		
		return books;
	}
	
	public BookVO createBook(BookVO book) {
		logger.info("Creating one book!");
		
		if(book == null)
			throw new RequiredObjectIsNullException();
		
		Book entity = BookMapper.parseObject(book, Book.class);
		
		BookVO vo = BookMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).getBookById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public BookVO updateBook(BookVO book) {
		logger.info("Updating one book!");
		
		if(book == null)
			throw new RequiredObjectIsNullException();
		
		Book entity = repository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		
		BookVO vo = PersonMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).getBookById(book.getKey())).withSelfRel());
		
		return vo;
	}
	
	public void deleteBook(Long id) {
		logger.info("Delete one book!");
		
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		repository.delete(entity);
	}
}
