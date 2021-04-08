package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class PictureController {

    private final Random random;
    //quotes from citaty.txt saved in this List
    private final List<String> listOfQuotes;

    public PictureController() throws IOException {
        random = new Random();
        listOfQuotes = readAllLines("citaty.txt");
    }

    @GetMapping("/")
    public ModelAndView choosePictureAndQuote(){
        ModelAndView result = new ModelAndView("quote");
        // random number of the quote from 0 to 7 - there are 8 quotes in total but the indices of the list are from 0 to 7
        int quoteNumber = random.nextInt(8);
        // random number from 0 to 9 - those are the names of the image files
        int pictureNumber = random.nextInt(10);

        result.addObject("quote", listOfQuotes.get(quoteNumber));
        result.addObject("picture", String.format("/images/%d.jpg", pictureNumber));

        return result;
    }

    private static List<String> readAllLines(String resource)throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InputStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }
}
