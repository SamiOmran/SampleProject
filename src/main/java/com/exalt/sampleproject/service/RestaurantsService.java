package com.exalt.sampleproject.service;

import com.exalt.sampleproject.dto.ResponseMessage;
import com.exalt.sampleproject.model.AllData;
import com.exalt.sampleproject.model.Contacts;
import com.exalt.sampleproject.model.Locations;
import com.exalt.sampleproject.model.Restaurants;
import com.exalt.sampleproject.repository.RestaurantsRepo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class RestaurantsService {
    private final RestaurantsRepo restaurantsRepo;
    private final LocationsService locationsService;
    private final ContactsService contactsService;
//    private final static Logger logger = LoggerFactory.getLogger(RestaurantsService.class);
    private ResponseMessage responseMessage = new ResponseMessage();

    public RestaurantsService(RestaurantsRepo restaurantsRepo, LocationsService locationsService, ContactsService contactsService) {
        this.restaurantsRepo = restaurantsRepo;
        this.locationsService = locationsService;
        this.contactsService = contactsService;
    }

    public ResponseMessage save(Restaurants restaurant) {
        restaurantsRepo.save(restaurant);

        responseMessage.setMessage("Successfully restaurant saved");
        responseMessage.setStatus(1);

        return responseMessage;
    }

    public Optional<Restaurants> findById(Long id) {
        return restaurantsRepo.findById(id);
    }

    public List<Restaurants> findAll() {
        return restaurantsRepo.findAll();
    }

    public ResponseMessage createRestaurant(AllData allData) {
        Restaurants restaurant = new Restaurants();
        restaurant.setName(allData.getName());
        responseMessage = save(restaurant);

        List<Contacts> contactsList = allData.getContacts();
        contactsService.createContact2(contactsList, restaurant);

        return responseMessage;
    }

    public ResponseMessage updateRestaurant(Long id, Restaurants updatedRestaurant) {
        Optional<Restaurants> optionalRestaurants = findById(id);

        if (optionalRestaurants.isPresent()) {
            optionalRestaurants.map(restaurant -> {
                restaurant.setName(updatedRestaurant.getName());
                return responseMessage = save(restaurant);
            });
        } else {
            responseMessage.setMessage("RestaurantId " + id + ", was not found.");
            responseMessage.setStatus(-1);
        }
        return responseMessage;
    }

    @Transactional
    public ResponseMessage deleteRestaurant(Long id) {
        Optional<Restaurants> optionalRestaurant = findById(id);

        if (optionalRestaurant.isPresent()) {
            responseMessage = contactsService.deleteContact(id);
            responseMessage = locationsService.deleteLocationByRestaurant(optionalRestaurant);
            restaurantsRepo.delete(optionalRestaurant.get());
        } else {
            responseMessage.setMessage("Not found");
            responseMessage.setStatus(-1);
        }

        return responseMessage;
    }

    public Object getRestaurantsInfo(String stringId) {
        try {
            Long id = Long.parseLong(stringId);
            Optional<Restaurants> optionalRestaurant = findById(id);

            if (optionalRestaurant.isPresent()) {
                AllData allData = new AllData();
                allData.setName(optionalRestaurant.get().getName());

                List<Locations> locationsList = locationsService.findLocationByRestaurantId(id);
                allData.setLocations(locationsList);

                List<Contacts> contactsList = new ArrayList<>();
                locationsList.forEach(location -> {
                    Optional<Locations> optionalLocation = Optional.of(location);
                    contactsList.addAll(contactsService.findContactsByLocation(optionalLocation));
                });
                allData.setContacts(contactsList);
                return allData;
            }
            return null;
        } catch (NumberFormatException exception) {
            return "Wrong url";
        }

    }

    public Object getAllRestaurants() {
        List<Restaurants> restaurantsList = findAll();
        List<Object> allDataList = new ArrayList<>();

        if (restaurantsList.isEmpty()) {
            return "No Restaurants found";
        } else {
            restaurantsList.forEach(restaurant -> {
                String idAsString = restaurant.getId().toString();
                allDataList.add(getRestaurantsInfo(idAsString));
            });
            return allDataList;
        }

    }

    public ResponseMessage createRestaurantUsingFile(MultipartFile fileData) {
        final String UPLOAD_DIR = "G:\\Training in companies\\Exalt\\SampleProject\\src\\main\\resources\\files";
        String fileName = fileData.getOriginalFilename();

        try {
            fileData.transferTo(new File(UPLOAD_DIR +"\\"+ fileName));
        } catch (Exception e) {
            responseMessage.setMessage("Couldn't upload file. " + e.getMessage());
            responseMessage.setStatus(-1);
        }
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        JSONArray array = new JSONArray();
        try  {
            FileReader file = new FileReader(UPLOAD_DIR +"\\"+ fileName);

            Object object = jsonParser.parse(file);
            array = (JSONArray) object;
            responseMessage.setMessage("Success reading file.");
            responseMessage.setStatus(1);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        array.forEach(rest -> parseRestaurantObject((JSONObject) rest));
        return responseMessage;
    }

    private void parseRestaurantObject(JSONObject newRestaurant) {
        String restaurantName = (String) newRestaurant.get("name");
        Restaurants restaurant = new Restaurants();
        restaurant.setName(restaurantName);

        save(restaurant);

        ArrayList<Contacts> contactsList = new ArrayList<>();
        ArrayList<Object> objectList = new ArrayList<>();

        objectList = (ArrayList<Object>) newRestaurant.get("contacts");

        objectList.forEach(obj -> {
            JSONObject jsonObject = (JSONObject) obj;
            String type = (String) jsonObject.get("type");
            String value = (String) jsonObject.get("value");

            JSONObject jsonLocation = (JSONObject) jsonObject.get("location");
            String city = (String) jsonLocation.get("city");
            String street = (String) jsonLocation.get("street");
            String section = (String) jsonLocation.get("section");
            Locations newLocation = new Locations(city, street, section, restaurant);

            Contacts newContact = new Contacts(type, value, newLocation);
            contactsList.add(newContact);
        });
        contactsService.createContact2(contactsList, restaurant);
    }
}
