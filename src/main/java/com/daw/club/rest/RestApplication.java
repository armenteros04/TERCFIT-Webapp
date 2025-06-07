package com.daw.club.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/contact")
class ContactResource {

    @GET
    @Path("/{id}/edit")
    @Produces(MediaType.TEXT_HTML)
    public String getEditForm(@PathParam("id") int id) {
        return """
        <div id="user-info" hx-target="this" hx-swap="outerHTML">
            <div>
                <label>First Name</label>: <input type="text" name="firstName" value="Joe">
            </div>
            <div>
                <label>Last Name</label>: <input type="text" name="lastName" value="Blow">
            </div>
            <div>
                <label>Email</label>: <input type="email" name="email" value="joe@blow.com">
            </div>
            <button hx-post="/api/contact/1/save" hx-target="#user-info" hx-swap="outerHTML">
                Save
            </button>
        </div>
        """;
    }

    @POST
    @Path("/{id}/save")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String saveContact(@PathParam("id") int id, @FormParam("firstName") String firstName,
                              @FormParam("lastName") String lastName, @FormParam("email") String email) {
        return """
        <div id="user-info" hx-target="this" hx-swap="outerHTML">
            <div><label>First Name</label>: """ + firstName + """
            </div>
            <div><label>Last Name</label>: """ + lastName + """
            </div>
            <div><label>Email</label>: """ + email + """
            </div>
            <button hx-get="/api/contact/1/edit" class="btn primary">
                Click To Edit
            </button>
        </div>
        """;

    }
}
