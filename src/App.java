import java.util.ArrayList;

import com.jutil.Logger.Logger;
import com.jutil.SimpleStore.Store;

public class App {
    static class SampleState {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Logger.info("App", "Hello", "world", "!");

        Logger.info("App", "Error", "Info", "!");

        ArrayList<Object> stateList = new ArrayList<Object>() {
            {
              add(new SampleState());
            }
          };
        
        Store.bind(stateList);

        Store.getState(SampleState.class).setId(0);
        Logger.info("APP", Store.getState(SampleState.class).getId());
        
        Store.getState(SampleState.class).setId(2);
        Logger.info("APP", Store.getState(SampleState.class).getId());

        SampleState sampleState = Store.getState(SampleState.class);
        sampleState.setId(100);
        Logger.info("APP", Store.getState(SampleState.class).getId());
        
    }
}
