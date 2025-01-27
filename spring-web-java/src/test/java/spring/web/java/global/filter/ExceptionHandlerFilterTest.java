package spring.web.java.global.filter;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerFilterTest {

	@InjectMocks
	private ExceptionHandlerFilter exceptionHandlerFilter;

	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private FilterChain filterChain;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@BeforeEach
	void setUp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Test
	void malformedJwtException() throws ServletException, IOException {
		// Given
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String message = "malformedJwtException";
		String responseBody = """
			{
			  "status": %s,
			  "title": "%s",
			  "detail": "%s"
			}
			""".formatted(status.value(), status.getReasonPhrase(), message);

		Mockito.doThrow(new MalformedJwtException(message)).when(filterChain).doFilter(request, response);
		Mockito.when(objectMapper.writeValueAsString(Mockito.any(ProblemDetail.class))).thenReturn(responseBody);

		// When
		exceptionHandlerFilter.doFilter(request, response, filterChain);

		// Then
		Assertions.assertThat(response.getStatus()).isEqualTo(status.value());
		Assertions.assertThat(response.getContentAsString()).isEqualTo(responseBody);
	}

	@Test
	void jwtException() throws ServletException, IOException {
		// Given
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		String message = "jwtException";
		String responseBody = """
			{
			  "status": %s,
			  "title": "%s",
			  "detail": "%s"
			}
			""".formatted(status.value(), status.getReasonPhrase(), message);

		Mockito.doThrow(new JwtException(message)).when(filterChain).doFilter(request, response);
		Mockito.when(objectMapper.writeValueAsString(Mockito.any(ProblemDetail.class))).thenReturn(responseBody);

		// When
		exceptionHandlerFilter.doFilter(request, response, filterChain);

		// Then
		Assertions.assertThat(response.getStatus()).isEqualTo(status.value());
		Assertions.assertThat(response.getContentAsString()).isEqualTo(responseBody);
	}

	@Test
	void runtimeException() throws ServletException, IOException {
		// Given
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String message = "runtimeException";
		String responseBody = """
			{
			  "status": %s,
			  "title": "%s",
			  "detail": "%s"
			}
			""".formatted(status.value(), status.getReasonPhrase(), message);

		Mockito.doThrow(new RuntimeException(message)).when(filterChain).doFilter(request, response);
		Mockito.when(objectMapper.writeValueAsString(Mockito.any(ProblemDetail.class))).thenReturn(responseBody);

		// When
		exceptionHandlerFilter.doFilter(request, response, filterChain);

		// Then
		Assertions.assertThat(response.getStatus()).isEqualTo(status.value());
		Assertions.assertThat(response.getContentAsString()).isEqualTo(responseBody);
	}
}
