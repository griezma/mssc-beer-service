package griezma.mssc.beerservice.api;

import griezma.mssc.beerservice.services.BeerService;
import griezma.mssc.beerservice.api.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    private static final String API_V1 = "/api/v1/beer";

    private final BeerService beerService;
    private final ServletContext context;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Page<BeerDto> listBeers(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "25") Integer pageSize,
                            @RequestParam(value = "name", required = false) String beerName,
                            @RequestParam(value = "style", required = false) String beerStyle,
                            @RequestParam(value = "inventory", required = false, defaultValue = "false") boolean inventory) {
        return beerService.findBeers(beerName, beerStyle, inventory, PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping("{beerId}")
    ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId, @RequestParam(value = "inventory", required = false, defaultValue = "false") Boolean includeInventory){
        return beerService.findBeerById(beerId, includeInventory)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody @Valid BeerDto beerDto){
        BeerDto saved = beerService.saveBeer(beerDto);
        URI location = makeURI(saved.getId().toString());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Valid BeerDto beerDto) {
        beerService.updateBeer(beerId, beerDto);
    }

    @DeleteMapping("/{beerId}")
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
