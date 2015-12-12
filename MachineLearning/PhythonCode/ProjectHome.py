import numpy as np
from sklearn.svm import SVC
import matplotlib.pyplot as plt
from sklearn import linear_model, preprocessing
from sklearn.preprocessing import Imputer
from sklearn.naive_bayes import GaussianNB
from sklearn.cluster import AgglomerativeClustering

import EvaluateModel

# Read the dataSet from CSV file
dataSet = np.genfromtxt('All States.csv', delimiter=',')
# dataSet = dataSet[:80000,:]

# # Business
labels = ['Open/close','Open on weekdays','Open of week ends','Business_Rating','Neighborhood count','Distance',
     'No_of_checkins','No. of checkins on weekday','No of check-ins on weekend','No. of reviews','No. of elite reviews',
     'No. of reviews in last 6 months','No. of reviews between 12 and 6 months','No. of reviews until last 1 year',
     'Average review length','Average review star rating','Average review useful ratings','Average review cool ratings',
     'Average review funny ratings','Average user star ratings','Average user useful ratings', 'Average user cool ratings',
     'Average user funny ratings','Stars']

# #Users
# labels = ['Elite_or_Not', 'Stars', 'Fans', 'Friends', 'Cool', 'Funny', 'Useful', 'Yelp_Months', 'Elite_Years',
#           'No_of_Reviews', 'Review_Star_Rating', 'Review_Cool', 'Review_Funny', 'Review_Useful']

# # Reviews
# labels = ['Review_Stars', 'Review_Length', 'Cool_Votes', 'Funny_Votes', 'Useful_Votes', 'User Elite in Current Year',
#           'Is User Elite', 'No of Elite Years', 'User Stars', 'User Cool Votes', 'User Funny Votes',
#           'User Useful Votes', 'Fans', 'Friends']

# testRecords = dataSet[:, [labels.index('Useful_Votes'), labels.index('User Useful Votes')]]
# clstr = AgglomerativeClustering(3, linkage='ward')
# clstr.fit(testRecords)
#
# print clstr.labels_

numRecords = len(dataSet)
# classLabelIndex = labels.index('Business_Rating')
classLabelIndex = labels.index('Open/close')
# classLabelIndex = labels.index('Elite_or_Not')
# classLabelIndex = labels.index('Useful_Votes')
# print classLabelIndex

# Extracting the class labels and the attributes from the dataSet

classLabels = dataSet[:, classLabelIndex]
dataRecords = np.delete(dataSet, [classLabelIndex], 1)
labels.__delitem__(classLabelIndex)


# print len(len(dataRecords[0]))

# labels.__delitem__(10)

# Handling missing values
imp = Imputer(missing_values='NaN', strategy='mean', axis=0)
dataRecords = imp.fit_transform(dataRecords)

# Scaling to achieve zero mean & unit variance
dataRecords = preprocessing.scale(dataRecords)

print(np.unique(classLabels))

# Discretizing the attribute values of the class labels.
# Resultant - 5 class problem for identifying the ratings of business
# for idx in range(numRecords):
#     classLabels[idx] = round(classLabels[idx], 0)
#     if classLabels[idx] >= 7:
#         classLabels[idx] = 1
#     else:
#         classLabels[idx] = 0
#
# print(np.unique(classLabels))

# SVM classifier
print('SVM classifier:')
svm_classifier = SVC(kernel='rbf', gamma=0.8, C=1.0)
[attribute_values_svm,accuracy_values_svm] = EvaluateModel.evaluate_model(svm_classifier, dataRecords, classLabels, labels)

# Logistic regression
print('Logistic regression:')
lr_classifier = linear_model.LogisticRegression(C=1.0)
[attribute_values_lr,accuracy_values_lr] = EvaluateModel.evaluate_model(lr_classifier, dataRecords, classLabels, labels)

# Naive Bayes classifier
print('Naive Bayes classifier:')
nb_classifier = GaussianNB()
[attribute_values_gnb,accuracy_values_gnb] = EvaluateModel.evaluate_model(nb_classifier, dataRecords, classLabels, labels)


# # Graphs
plt.plot(attribute_values_svm, accuracy_values_svm)
plt.plot(attribute_values_lr, accuracy_values_lr)
plt.plot(attribute_values_gnb, accuracy_values_gnb)
plt.xlabel('Number of Attributes')
plt.ylabel('Accuracy')
# plt.title('Usefulness of a Review')
# plt.title('Business Rating')
plt.title('Business Status')

plt.legend(['SVM','LR','GNB'])


plt.show()