package com.techprimers.stock.dbservice.resource;

import com.techprimers.stock.dbservice.model.Quote;
import com.techprimers.stock.dbservice.model.Quotes;
import com.techprimers.stock.dbservice.repository.QuotesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DbServiceResource {

    private QuotesRepository quotesRepository;

    public DbServiceResource(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
    }

    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable("username") final String username){

        return getQuotesByUsername(username);
    }

    @PostMapping("/add")
    public List<String> add(@RequestBody final Quotes quotes){

        quotes.getQuotes()
                .stream()
                .forEach(quote -> quotesRepository.save(new Quote(quotes.getUsername(), quote)));

        return getQuotesByUsername(quotes.getUsername());
    }

    @DeleteMapping("/{username}")
    public void delete(@PathVariable("username") final String username){

        List<Quote> quotes = quotesRepository.findByUsername(username);

        quotes.stream()
                .forEach(quote -> quotesRepository.delete(quote));
    }

    private List<String> getQuotesByUsername(@PathVariable("username") String username) {
        return quotesRepository.findByUsername(username)
                .stream()
                .map(quote -> quote.getQuote())
                .collect(Collectors.toList());
    }
}
