function nextGeneration(currentGeneration) {
    var newGeneration = new Array(currentGeneration.length).fill(0);
    for (var i = 1; i < currentGeneration.length - 1; i++) {
        newGeneration[i] = rule90(currentGeneration[i - 1], currentGeneration[i], currentGeneration[i + 1]);
    }
    return newGeneration;
}

function rule90(a, b, c) {
    return a ^ c;
}
