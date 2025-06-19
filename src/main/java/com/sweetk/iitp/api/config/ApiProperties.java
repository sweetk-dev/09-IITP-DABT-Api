package com.sweetk.iitp.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "api")
public class ApiProperties {
    private Version version;
    private RateLimit rateLimit;

    public Version getVersion() { return version; }
    public void setVersion(Version version) { this.version = version; }
    public RateLimit getRateLimit() { return rateLimit; }
    public void setRateLimit(RateLimit rateLimit) { this.rateLimit = rateLimit; }

    public static class Version {
        private String current;
        private List<String> supported;
        public String getCurrent() { return current; }
        public void setCurrent(String current) { this.current = current; }
        public List<String> getSupported() { return supported; }
        public void setSupported(List<String> supported) { this.supported = supported; }
    }

    public static class RateLimit {
        private boolean enabled;
        private int capacity;
        private int timeWindow;
        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        public int getCapacity() { return capacity; }
        public void setCapacity(int capacity) { this.capacity = capacity; }
        public int getTimeWindow() { return timeWindow; }
        public void setTimeWindow(int timeWindow) { this.timeWindow = timeWindow; }
    }
} 