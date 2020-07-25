package MachineLearning;

import Model.Vitals.BiologicalParameter;

import java.util.Date;

public class Glucose extends BiologicalParameter {
        private double glucose;
        private Date effectiveDateTime;

        public Glucose(double newglucose, Date newEffectiveDateTime) {
            this.glucose = newglucose;
            this.effectiveDateTime = newEffectiveDateTime;
        }


        /**
         * @return int: GLucose level
         */
        @Override
        public Object getMeasurement() {
            return this.glucose;
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
            String glucoseLevel = String.valueOf(this.glucose);
            String time = this.effectiveDateTime.toString();
            return glucoseLevel;
        }
    }


