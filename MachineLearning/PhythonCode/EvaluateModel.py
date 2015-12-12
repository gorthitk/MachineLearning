
from sklearn import cross_validation
from sklearn.feature_selection import SelectFpr
from sklearn.feature_selection import f_classif


# Evaluating the classifier using k-field cross validation.
def evaluate_model(classifier, data_records, class_labels, labels):

    attribute_values = []
    accuracy_values = []

    # Scoring the attributes using F_test and false positive rate
    clf = SelectFpr(f_classif, alpha=0.9)
    clf.fit(data_records, class_labels)
    print(clf.scores_)
    print('\n')

    ranked_attr_indices = [0] * len(clf.scores_)
    for i, x in enumerate(sorted(range(len(clf.scores_)), key=lambda y: clf.scores_[y])):
        ranked_attr_indices[x] = i

    # Performing a 4-fold cross validation against varying number of attributes. The attributes are chosen
    # on the basis of their scores
    for idx in range(2, len(ranked_attr_indices)):
        filtered_records = data_records[:, ranked_attr_indices[:idx]]
        for idx2 in ranked_attr_indices[:idx]:
            print(labels[idx2])
        validation_score = cross_validation.cross_val_score(classifier, filtered_records, class_labels, cv=5)
        accuracy = max(validation_score) * 100
        attribute_values.append(idx)
        accuracy_values.append(accuracy)
        print('Cross validation score - ' + str(idx) + ' attributes :' + str(validation_score) + '\n')

    return (attribute_values, accuracy_values)
