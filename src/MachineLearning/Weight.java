package MachineLearning;

import Model.Vitals.BiologicalParameter;

import java.util.Date;

public class Weight extends BiologicalParameter

    {
        private double weight;
        private Date effectiveDateTime;

        public Weight(double newWeight, Date newEffectiveDateTime) {
        this.weight = newWeight;
        this.effectiveDateTime = newEffectiveDateTime;
    }


        /**
         * @return int: weight level
         */
        @Override
        public Object getMeasurement() {
        return this.weight;
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
        String weight = String.valueOf(this.weight);
        String time = this.effectiveDateTime.toString();
        return weight;
    }
    }




