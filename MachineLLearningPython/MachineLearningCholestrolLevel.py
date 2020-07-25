import numpy as np
import pandas as pd
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import confusion_matrix


class MLcholestrolValue:

    def __init__(self, nameOfFile):
        self.nameOfFile = nameOfFile




    def dataAnalysis(self):
        # Store the data into a variable
        self.df = pd.read_csv(self.nameOfFile)

        # Print the data
        self.df.head(7)

        self.df.isnull().values.any()
        self.df.describe()
        # Get a count of the number of patients with high cholestrol value and without it
        self.df['cholestrol'].value_counts()

        # Look at the number of people with a high cholestrol
        sns.countplot(x='age', hue='cholestrol', data=self.df, palette='colorblind',
                      edgecolor=sns.color_palette('dark', n_colors=1))

        # Get the corrrelation of the colums
        self.df.corr()

        # visualize the data

        plt.figure(figsize=(7, 7))
        sns.heatmap(self.df.corr(), annot=True, fmt='.0%')

        # Remove the id column
        self.df = self.df.drop('id',  axis =1)





    def mlWork(self):
        # Splitt the data into feature data and target data
        X = self.df.iloc[:, : -1].values
        Y = self.df.iloc[:, -1].values

        # Split the data again, into 75% TRAINING DATA SET AND 25% TESTING DATA SET



        X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.25, random_state=1)

        # Feature Scaling
        # scale the values in the data to be values b/w 0 and 1 inclusive

        sc = StandardScaler()
        X_train = sc.fit_transform(X_train)

        X_test = sc.transform(X_test)

        # Random Forest Classifier



        forest = RandomForestClassifier(n_estimators=10, criterion='entropy', random_state=1)

        forest.fit(X_train, Y_train)



        # Test the model accuracy on the training data set

        model = forest
        scorecValue = model.score(X_train, Y_train)

        print("Score value from the X_train and Y_train = " + str(scorecValue))

        cm = confusion_matrix(Y_test, model.predict(X_test))

        TN = cm[0][0] # True Negative

        TP = cm[1][1] # True Positive
        FN = cm[1][0] # False Negative
        FP = cm[0][1] # False Positive

        # print the consudion the matrix
        # print(model)
        print(cm)

        # print themodels accuracy

        print('Model Test Accuracy using the confusion Matrix={}'.format((TP + TN) / (TP + TN + FN + FP)))


ml = MLcholestrolValue("filterData1.csv")
ml.dataAnalysis()
ml.mlWork()