package griezma.mssc.beerservice.api.mapper;

import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.api.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateTimeMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDto beerToDto(Beer beer);
    Beer dtoToBeer(BeerDto beerDto);
}
