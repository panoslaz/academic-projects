package calculations;


import calculations.model.CalculationValues;
import calculations.model.DiameterValues;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.text.DecimalFormat;


/**
 * Contains the rest entry points of the web application
 */
@Path("/calculator")
public class CalculationController {


    /**
     * Returns diameter for the given input
     */
    @GET
    @Path("/diameter")
    @Produces("application/json")
    public DiameterValues getDiameter(@QueryParam("qin") double qin, //input value for the tube's inflow
                                      @QueryParam("din") double din, //input value for diameter
                                      @QueryParam("tin") double tin, //input value for the tube's maximum depth
                                      @QueryParam("roughness") double roughness, //input value for the tube's roughness
                                      @QueryParam("angle") double angle)//input value for the tube's angle
     {

        return CalculationService.computeDiameterValues(qin, din, tin, roughness, angle);
    }


    /**
     * Returns all the values that need to be calculated.
     *
     * @param qin input value for the tube's inflow
     * @param tin input value for the tube's maximum depth
     * @param roughness input value for the tube's roughness
     * @param angle input value for the tube's angle
     * @param dFinal final diameter value
     * @return
     */
    @GET
    @Path("/allValues")
    @Produces("application/json")
    public CalculationValues calculations(
            @QueryParam("qin") double qin,
            @QueryParam("tin") double tin,
            @QueryParam("roughness") double roughness,
            @QueryParam("angle") double angle,
            @QueryParam("diameter") double dFinal) {

        return CalculationService.getCalculationValues(qin, tin, roughness, angle, dFinal);
    }


}






