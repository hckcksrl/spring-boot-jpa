package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	/**
	 * Hibernate5Module을 스프링 빈으로 등록하면 LAZY LOADING 무시
	 */
	@Bean
	Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}

	/**
	 * 강제로 지연로딩한다. 이 옵션을 키면 양방향 연관관계를 계속 로딩한다.
	 * @JsonIgnore옵션을 한곳에 설정해서 무한루프 로딩을 없애야한다.
	 */
//	@Bean
//	Hibernate5Module hibernate5Module() {
//		Hibernate5Module hibernate5Module = new Hibernate5Module();
//		hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
//	}

}
