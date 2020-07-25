package MachineLearning;

import Model.Vitals.BiologicalParameter;

import java.util.Date;

public class Height  extends BiologicalParameter {
        private double height;
        private Date effectiveDateTime;

        public Height(double newHeight, Date newEffectiveDateTime) {
            this.height = newHeight;
            this.effectiveDateTime = newEffectiveDateTime;
        }


        /**
         * @return int: height level
         */
        @Override
        public Object getMeasurement() {
            return this.height;
        }
        @Override
        public Object getEffectiveTimeDate(){
            return this.getEffectiveTimeDate();
        }


        public String toStringTime(){
            String time = this.effectiveDateTime.toString();
            return time;
        }

        public String toStringVitals() {
            String height = String.valueOf(this.height);
            String time = this.effectiveDateTime.toString();
            return height;
        }
    }


