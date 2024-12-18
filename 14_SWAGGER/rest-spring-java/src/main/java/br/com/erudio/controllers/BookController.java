package br.com.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.services.BookServices;
import br.com.erudio.services.PersonServices;
import br.com.erudio.uitl.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Endpoints for Managing Books")
public class BookController {
	
	@Autowired
	private BookServices service;
	
	@GetMapping(
				value = "/{id}",
				produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
			)
	@Operation(
			summary = "Finds a book",
			description = "Finds a book",
			tags = { "Book" },
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = PersonVO.class))
					),
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
			}
	)
	public BookVO getBookById(
				@PathVariable(value = "id") Long id
			) {
		return service.findById(id);
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(
			summary = "Finds all books",
			description = "Finds all books",
			tags = { "Book" },
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = {
									@Content(
										mediaType = "application/json",
										array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
									)
							}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
			}
	)
	public List<BookVO> getAllBooks(){
		return service.findAll();
	}
	
	@PostMapping(
				consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
				produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
	)
	@Operation(
			summary = "Create a new book",
			description = "Create a new book by passing in a JSON, XML or YAML Representation of the book",
			tags = { "Book" },
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = PersonVO.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
			}
	)
	public BookVO createBook(@RequestBody BookVO book) {
		return service.createBook(book);
	}
	
	@PutMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
		)
	@Operation(
			summary = "Updates a book",
			description = "Updates a book by passing in a JSON, XML or YAML Representation of the book",
			tags = { "Book" },
			responses = {
					@ApiResponse(description = "Updated", responseCode = "200", 
							content = @Content(schema = @Schema(implementation = PersonVO.class))
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
			}
	)
	public BookVO updateBook(@RequestBody BookVO book) {
		return service.updateBook(book);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(
			summary = "Deletes a book",
			description = "Deletes a book by passing in a JSON, XML or YAML Representation of the book",
			tags = { "Book" },
			responses = {
					@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
			}
	)
	public ResponseEntity<?> deleteBookById(
				@PathVariable(value = "id") Long id
			) {
		service.deleteBook(id);
		
		return ResponseEntity.noContent().build();
	}
}
