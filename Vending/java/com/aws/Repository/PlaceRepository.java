package com.aws.Repository;

import com.aws.model.Place;
import org.springframework.data.repository.CrudRepository;

public interface PlaceRepository extends CrudRepository<Place, String> {
}
