import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class VolcanoAnalyzer {
    private List<Volcano> volcanos;

    public void loadVolcanoes(Optional<String> pathOpt) throws IOException, URISyntaxException {
        try{
            String path = pathOpt.orElse("volcano.json");
            URL url = this.getClass().getClassLoader().getResource(path);
            String jsonString = new String(Files.readAllBytes(Paths.get(url.toURI())));
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            volcanos = objectMapper.readValue(jsonString, typeFactory.constructCollectionType(List.class, Volcano.class));
        } catch(Exception e){
            throw(e);
        }
    }

    public Integer numbVolcanoes(){
        return volcanos.size();
    }

    //add methods here to meet the requirements in README.md
    
    public List<Volcano> eruptedInEighties(){
       // IntStream myIntStream = 
       return this.volcanos.stream().filter(obj -> obj.getYear() >= 1980 && obj.getYear() < 1990  ).collect(Collectors.toList());
    }



    // .map(m -> m.get("position").toString()).collect(Collectors.toList())

     public String[] highVEI(){
        List<String> myVol =  volcanos.stream().filter(vol -> vol.getVEI() >= 6).map(i->i.getName()).collect(Collectors.toList()); 
       return  myVol.toArray(new String[0]);
       
     }



    //  public List<Volcano> mostDeadly(){

    //     // Integer myMax = volcanos.stream().max((x,y) -> x.getDEATHS().compareTo(y.getDEATHS()));

    //     // Integer yourMax = volcanos.stream().max(Comparator.comparing(Volcano::getDEATHS))

    //     myMax = volcanos.stream().max(volcanos:: getDEATHS);

    //   return  volcanos.stream().filter(vol->vol.getDEATHS() == myMax  ).collect(Collectors.toList()); 
        
    //  }

    // public List<Volcano> mostDeadly(){

    //         List<Volcano> myVolcanos = volcanos.stream()
    //         .sorted(Comparator.comparingInt(Volcano::getDEATHS)).limit(1)
    //         .collect(Collectors.toList());
    
    //         return myVolcanos.stream().skip(stream().count()-1)  ;

    //         return myVolcanos.stream().sorted(Collections.reverseOrder()).findFirst().                  .orElse(null);


    // }
    
    // public Volcano mostDeadly(){
    //      return volcanos.stream()
    //     .sorted(Comparator.comparingInt(Volcano::getDEATHS)).limit(1).collect(Collector.toList());

    // }
                                                                                                                            
    // long l=500;  
    // int i=(int)l;  


    // Return the percentage of eruptions that caused tsunamis.

    public double causedTsunami(){
        
        double volsThatCauseTsu =   volcanos.stream().filter(vol-> vol.getTsu().equals("tsu") ).collect(Collectors.toList()).size();

        double totalVols = volcanos.size();
        
        double per = (volsThatCauseTsu / totalVols)*100;

        return per;
        
        
    }
}
