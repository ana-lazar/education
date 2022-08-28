from predictions import Predictions


def run():
    predictions = Predictions()
    # predictions.e1_error_prediction('data/single_target')
    # predictions.e2_binary_classification()
    # predictions.e3_matrix_classification()
    # predictions.e4_multi_target_prediction('data/multi_target')
    # predictions.e5_multi_classification('data/binary_class')
    # predictions.e5_multi_classification('data/multi_class')
    # predictions.e6_regression_loss('data/single_target')
    # predictions.e7_classification_loss('data/binary_prob')
    # predictions.e7_classification_loss('data/multi_prob')
    predictions.e8_multi_label_loss("data/multi_label")


if __name__ == '__main__':
    run()
