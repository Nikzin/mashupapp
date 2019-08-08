package nz.mashupapp.Controllers;

import nz.mashupapp.Consumed.*;
import nz.mashupapp.Services.AlbumAndImageList;
import nz.mashupapp.Services.WikiExtractService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class MashupController {
   private AlbumAndImageList albumAndImageList;
    private WikiExtractService wikiExtractService;

    MashupController(AlbumAndImageList albumAndImageList, WikiExtractService wikiExtractService){
        this.albumAndImageList=albumAndImageList;
        this.wikiExtractService=wikiExtractService;
        }

    @RequestMapping("/mashup/{mbid}")
    //http://localhost:8081/mashup/5b11f4ce-a62d-471e-81fc-a69a8278c7da
    public TheResponse theResponse(@PathVariable("mbid") String mbid) {
      //  public String theResponse(@PathVariable("mbid") String mbid) {
      TheResponse theResponse = new TheResponse();
        String test = "testing"+ mbid;

        //get list of albums
      ArrayList<AlbumAndImage> albumAndImageArrayList =  albumAndImageList.getList(mbid);

        //get info from Wikipedia
       String wikiExtract =wikiExtractService.getwikiExtract(mbid);

       theResponse.setMbid(mbid);
       theResponse.setAlbums(albumAndImageArrayList);
       theResponse.setDescription(wikiExtract);

    return theResponse;
       // return test;
    }
}
