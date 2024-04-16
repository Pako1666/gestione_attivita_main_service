package it.gestore_attivita.gestore_attivita;

import it.gestore_attivita.gestore_attivita.rest.attività.model.AttivitaModel;
import it.gestore_attivita.gestore_attivita.rest.attività.model.AttivitaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class GestoreAttivitaApplicationTests {


	@Test
	void contextLoads() {
		System.out.println("ciao dal test");
	}

}
