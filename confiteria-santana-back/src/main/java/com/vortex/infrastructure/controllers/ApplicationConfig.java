package com.vortex.infrastructure.controllers;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "API confiteria",
                version = "1.0",
                description = "Esta es una API que gestiona la aplicación"
        ),
        tags = {
                @Tag(name = "Address", description = "Operaciones relacionadas con direcciones"),
                @Tag(name = "Alegen", description = "Operaciones relacionadas con alergenos"),
                @Tag(name = "Category", description = "Operaciones relacionadas con categorias"),
                @Tag(name = "PaymentMethod", description = "Operaciones relacionadas con metodos de pago"),
                @Tag(name = "Payment", description = "Operaciones relacionadas con pagos"),
                @Tag(name = "ProductPhoto", description = "Operaciones relacionadas con fotos de productos"),
                @Tag(name = "ShippingTracking", description = "Operaciones relacionadas con seguimientos"),
                @Tag(name = "Stock", description = "Operaciones relacionadas con stock"),
                @Tag(name = "StockMovements", description = "Operaciones relacionadas con movimientos de almacen"),
                @Tag(name = "Product", description = "Operaciones relacionadas con productos"),
                @Tag(name = "Order", description = "Operaciones relacionadas con pedidos"),
                @Tag(name = "User", description = "Operaciones relacionadas con usuarios")
        }
)
@ApplicationPath("/api")

public class ApplicationConfig extends Application {


         @APIResponses({
                 @APIResponse(responseCode = "200", description = "Operación exitosa"),
                 @APIResponse(responseCode = "400", description = "Solicitud incorrecta"),
                 @APIResponse(responseCode = "500", description = "Error interno del servidor")
         })
         public void configure() {
         // Configuración adicional si es necesario
         }


}
