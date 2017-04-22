package modules.resources;

import californium.core.CoapResource;
import californium.core.coap.CoAP;
import californium.core.server.resources.CoapExchange;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Artur Karolak on 2017-02-22.
 */
public class MainIconResource extends CoapResource {
    private static final String NAME = "main_icon";
    private final BufferedImage MAIN_ICON;

    public MainIconResource(BufferedImage imageIcon) {
        super(NAME);
        MAIN_ICON = imageIcon;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            exchange.respond(CoAP.ResponseCode.CONTENT, imageToByteArray(MAIN_ICON));
        } catch (IOException e) {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.FORBIDDEN);
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.FORBIDDEN);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.FORBIDDEN);
    }

    private static byte[] imageToByteArray(BufferedImage image) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
