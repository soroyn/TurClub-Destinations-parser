package TelegaBotPac.Parser;

import TelegaBotPac.core.model.Destination;
import TelegaBotPac.core.model.Route;
import TelegaBotPac.service.DriverServiceChrome;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ParsePIK implements ClubParseble {

    DriverServiceChrome driverServiceChrome = new DriverServiceChrome();

    public static final String HOME_URL = "https://turclub-pik.ru";
    public static final String HOME_SEARCH_URL = HOME_URL + "/search/";

    /**
     * @return
     */
    public List<Destination> getParsedUrl() {
        return DoParsingMain.getDestination(
                driverServiceChrome.getDriver(HOME_SEARCH_URL));
    }

    /**
     * @return
     */
    public List<String> getTitle() {
        return getParsedUrl().stream().map(Destination::getNameDestination).collect(Collectors.toList());
    }

    public HashMap<Destination, List<Route>> getInfoRoute() throws IOException {

        HashMap<Destination, List<Route>> resMap = new HashMap<>();

        WebDriver driver = driverServiceChrome.getDriver(HOME_SEARCH_URL);
        try {
            List<Destination> destinations = DoParsingMain.getDestination(driver);

            System.out.println(destinations.size());

            for (Destination destination : destinations) {
                System.out.println(destination.toString());
                driver.get(destination.getLink());
                resMap.put(destination, DoParsingBranch.getParsed(driver));
            }
        }finally {
            driver.close();
        }
        return resMap;
    }

}
