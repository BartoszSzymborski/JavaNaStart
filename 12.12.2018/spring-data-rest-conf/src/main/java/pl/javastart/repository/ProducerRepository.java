package pl.javastart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.javastart.model.Producer;

@RepositoryRestResource(path = "manufactures")
public interface ProducerRepository extends JpaRepository<Producer, Long> {
}
