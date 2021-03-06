/*****************************************************************c******************o*******v******id********
 * File: ProductResource.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : Anton Hrytsyk
 *
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.utils.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.utils.MyConstants.PRODUCT_RESOURCE_NAME;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static com.algonquincollege.cst8277.utils.MyConstants.RESOURCE_PATH_ID_PATH;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.algonquincollege.cst8277.ejb.CustomerService;
import com.algonquincollege.cst8277.models.ProductPojo;

@Path(PRODUCT_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @EJB
    protected CustomerService customerServiceBean;

    @Inject
    protected ServletContext servletContext;
	

	
    /**
     * response that displays for adding new product
     * @param new product
     * @return Response
     */
    @RolesAllowed({ADMIN_ROLE})
    @POST
    public Response addProduct(ProductPojo newProduct) {
      Response response = null;
      ProductPojo newProductWithIdTimestamps = customerServiceBean.persistProduct(newProduct);
      response = Response.ok(newProductWithIdTimestamps).build();
      return response;
    }
    
    /**
     * response that displays for updating product by id
     * @param id, product with updates
     * @return Response
     */
    @RolesAllowed({ADMIN_ROLE})
    @PUT
    @Path("{id}")
    public Response updateProduct(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id, ProductPojo productWithUpdates) {
        Response response = null;
        ProductPojo updatedProduct = customerServiceBean.getProductById(id);
        updatedProduct = customerServiceBean.updateProduct(id, productWithUpdates);
        response = Response.ok(updatedProduct).build();
        return response;
    }

    /**
     * response that displays for getting all products
     * @return Response
     */
    @GET
    public Response getProducts() {
        servletContext.log("retrieving all products ...");
        List<ProductPojo> custs = customerServiceBean.getAllProducts();
        Response response = Response.ok(custs).build();
        return response;
    }

    /**
     * response that displays for getting product by id
     * @param id
     * @return Response
     */
    @GET
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getProductById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        servletContext.log("try to retrieve specific product " + id);
        ProductPojo theProduct = customerServiceBean.getProductById(id);
        Response response = Response.ok(theProduct).build();
        return response;
    }
}