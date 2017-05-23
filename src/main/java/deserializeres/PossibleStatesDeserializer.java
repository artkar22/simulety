package deserializeres;

import com.google.gson.*;
import modules.SimuletsStateToSend;

import java.lang.reflect.Type;

/**
 * Created by Artur Karolak on 2017-05-23.
 */
public class PossibleStatesDeserializer implements JsonDeserializer<SimuletsStateToSend> {

    @Override
    public SimuletsStateToSend deserialize(JsonElement json, Type typeOfT,
                                           JsonDeserializationContext context) throws JsonParseException {

        JsonArray array = json.getAsJsonArray();
        return new SimuletsStateToSend(array.get(0).getAsString(), array.get(1).getAsString().getBytes(), array.get(2).getAsString().getBytes());

    }
}
