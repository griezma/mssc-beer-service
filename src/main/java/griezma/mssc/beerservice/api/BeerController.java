package griezma.mssc.beerservice.api;

import griezma.mssc.beerservice.services.BeerService;
import griezma.mssc.brewery.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BeerController {
    private static final String API_V1 = "/api/v1/";

    private final BeerService beerService;

    @GetMapping(path = "/beer",  produces = MediaType.APPLICATION_JSON_VALUE)
    Page<BeerDto> listBeers(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "25") Integer pageSize,
                            @RequestParam(value = "name", required = false) String beerName,
                            @RequestParam(value = "style", required = false) String beerStyle,
                            @RequestParam(value = "inventory", required = false, defaultValue = "false") boolean inventory) {
        return beerService.listBeers(beerName, beerStyle, inventory, PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping("/beer/{beerId}")
    ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId, @RequestParam(value = "inventory", required = false, defaultValue = "false") Boolean includeInventory){
        return beerService.findBeerById(beerId, includeInventory)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/beerupc/{upc}")
    ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") String upc) {
//        log.debug("findBeerByUpc: {}", upc);
        return beerService.findBeerByUpc(upc)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/beer")
    ResponseEntity saveNewBeer(@RequestBody @Valid BeerDto beerDto){
        BeerDto saved = beerService.saveBeer(beerDto);
        URI location = makeURI(saved.getId().toString());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/beer/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Valid BeerDto beerDto) {
        beerService.updateBeer(beerId, beerDto);
    }

    @DeleteMapping("/beer/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId) {
        beerService.removeBeerById(beerId);
    }

    private URI makeURI(String part) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build(API_V1, part);
    }
}
