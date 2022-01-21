# WeightedFlashCard
This application simulates a shuffled flashcard deck study technique, with the added benefit of weighting selection based on previous answers. 
Therefore cards that frequently elicit incorrect responses will be more likely to be selected in the future than thoes that receive erroneous answers
comparatively less. In the example provided countries are randomly selected from a deck and the user must respond with the correct capital from a defined
set of acceptable answers. Following a provided answer, the new weight of that card will be presented, as well as the correct answer should the given
response be incorrect. Card's weights are saved to disk, and therefore persist throughout subsequent iterations of the game. Should you desire to 
reset all the weights, you may run the RESET.sh script.

### How the weighting algorithm works <br />
The weight for each given card ranges from (non-inclusive) 0 to 10, where a card with weight 10 is 10 times more likely to be selected than a card
with weight 1. When a correct answer is given, the weight is halved. When an incorrect answer is given, the weight increases by an "incorrectness" 
factor, which is determined by the lexicographic similarity (Levenshtein distance) between the correct answer and the provided answer. The similarity
is determined by the number of operations necessary to transform the answer into a correct one, where an operation is defined as the insertion, deletion, or
alteration of a character. The proportion of operations to the length of the correct answer determines the factor by which the weight will increase, which will
range from 1 (non-inclusive) and 2. For example, if the given card is "United Kingdom" and the response is "Londn", the weight will increase by a smaller amount
(x1.16666) than if the response was "Paris" (x2.0).

