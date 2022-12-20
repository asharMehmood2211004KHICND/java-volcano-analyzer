import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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



    //  Return the eruption with the highest number of recorded deaths.



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


    public Volcano mostDeadly(){
        return volcanos.stream().max(Comparator.comparing(i->Integer.parseInt(i.getDEATHS().isEmpty()?"0":i.getDEATHS()))).get();
    }




    // Return the percentage of eruptions that caused tsunamis. , passed.

    public double causedTsunami(){
        
        double volsThatCauseTsu =   volcanos.stream().filter(vol-> vol.getTsu().equals("tsu") ).collect(Collectors.toList()).size();

        double totalVols = volcanos.size();
        
        double per = (volsThatCauseTsu / totalVols)*100;

        return per;
        
        
    }


    // Return the most common type of volcano in the set.

    // public String mostCommonType(){
    //     Map<String,Volcano> myVols =  new HashMap<String,Volcano>();

    //    List<Map<String,Volcano>> myListOfMaps =  volcanos.stream().map(i->myVols.put(i.getType(), i)).collect(Collectors.toList());

    //    Map<String,Volcano> myNewVols =  new HashMap<String,Volcano>();

    //     // key, map.get(key) + 1


    // }

    // public String mostCommonType(){
        
    //     Map<String,Volcano> myVols =  new HashMap<String,Volcano>();
    //     List<Volcano> myListOfMaps =  volcanos.stream().map(i->myVols.put(i.getType(), i)).collect(Collectors.toList());
        
    //     Map<String,List<Volcano>> volsGrouped = volcanos.stream().collect(Collectors.groupingBy(Volcano::getCountry));
        
    //     volsGrouped.stream()        
        
    // }
    


    public String mostCommonType(){

        List<String> volTypes =  volcanos.stream().map(i->i.getType()).distinct().collect(Collectors.toList());
        List<Integer> count = new ArrayList<>();
        volTypes.forEach(i->count.add(volcanos.stream().filter(j->j.getType().equals(i)).collect(Collectors.toList()).size())); 
       
         Integer maxValue = Collections.max(count);
         return volTypes.get(count.indexOf(maxValue));
  
 
     }


    // Return the number of eruptions when supplied a country as an argument.



    public Integer eruptionsByCountry(String countryName ){
      double countryCountList =  volcanos.stream().filter(vol->vol.getCountry().equals(countryName)).collect(Collectors.toList()).size();
      int myVale = (int)  countryCountList;
      return myVale;
    }


    // Return the average elevation of all eruptions.

    public Double  averageElevation(){

        
        List<Integer> elevationsList =    volcanos.stream().map(i->i.getElevation()).collect(Collectors.toList());
        double sum =  elevationsList.stream().reduce(0, (a, b) -> a + b); 
        double totalVols = volcanos.size();
        double avgElevation = sum/totalVols;
        return avgElevation;
   
    }

    // most common


   

    

    //  Return the percentage of eruptions that occurred in the Northern Hemisphere.


    public double percentNorth(){
        
       double north =  volcanos.stream().filter(i->i.getLatitude() >= 0 && i.getLatitude() < 90 ).collect(Collectors.toList()).size();
       double total = volcanos.size();
       double locPer =  (north/total)*100;
        return locPer;

    }

    // Return the names of eruptions that occurred after 1800, that did NOT cause a tsunami, happened in the Southern Hemisphere, and had a VEI of 5.


     public String[] manyFilters(){

        List<String> manyFilterVol =  volcanos.stream().filter(i->i.getYear()>1800 && i.getTsu().equals("") && i.getLatitude() >= -90 && i.getLatitude() <= 0 && i.getVEI()==5).map(j->j.getName()).collect(Collectors.toList()); 
        return  manyFilterVol.toArray(new String[0]);

        
     }




    
    // Return the names of eruptions that occurred at or above an elevation passed in as an argument.


   public String[] elevatedVolcanoes(int elevation){

    List<String> myList =  volcanos.stream().filter(i->i.getElevation()>= elevation).map(j->j.getName()).collect(Collectors.toList());

      return  myList.toArray(new String[0]);

   }

// filter(obj -> obj.getYear() >= 1980 && obj.getYear() < 1990  ).c



 
}
