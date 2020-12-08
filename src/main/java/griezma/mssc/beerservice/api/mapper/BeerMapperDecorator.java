package griezma.mssc.beerservice.api.mapper;

import griezma.mssc.beerservice.data.Beer;
import griezma.mssc.beerservice.services.inventory.BeerInventoryService;
import griezma.mssc.beerservice.api.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BeerMapperDecorator implements BeerMapper {
    @Autowired @Qualifier("delegate")
    private BeerMapper delegate;

    @Autowired
    private BeerInventoryService inventoryService;

    @Override
    public BeerDto beerToDto(Beer beer) {
        Integer onHand = inventoryService.getOnhandInventory(beer.getId());
        BeerDto dto = delegate.beerToDto(beer);
        dto.setQuantityOnHand(onHand);
        return dto;
    }

    @Override
    public Beer dtoToBeer(BeerDto beerDto) {
        return delegate.dtoToBeer(beerDto);
    }
}