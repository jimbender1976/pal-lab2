package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

   private final String port;
   private final String memoryLimit;
   private final String cfInstanceIndex;
   private final String cfInstanceAddress;

    public EnvController(@Value("${PORT:NOT SET}") String port,
                         @Value("${MEMORY_LIMIT:NOT SET}") String memory_limit,
                         @Value("${CF_INSTANCE_INDEX:NOT SET}") String cf_instance_index,
                         @Value("${CF_INSTANCE_ADDR:NOT SET}") String cf_instance_addr)
    {
       this.port = port;
       this.memoryLimit = memory_limit;
       this.cfInstanceAddress = cf_instance_addr;
       this.cfInstanceIndex = cf_instance_index;
    }


    @GetMapping("/env")
    public Map<String, String> getEnv() {

        Map<String, String> env = new HashMap<>();

        env.put("PORT", port);
        env.put("MEMORY_LIMIT", memoryLimit);
        env.put("CF_INSTANCE_INDEX", cfInstanceIndex);
        env.put("CF_INSTANCE_ADDR", cfInstanceAddress);

        return env;

    }
}
