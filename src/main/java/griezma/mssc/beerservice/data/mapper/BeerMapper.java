package griezma.mssc.beerservice.data.mapper;

import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = DateTimeMapper.class)
public interface BeerMapper {
    BeerDto asBeerDto(Beer beer);
    Beer toBeer(BeerDto beerDto);
}
