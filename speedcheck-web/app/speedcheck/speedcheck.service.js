class ResultsService {

    constructor($http) {
        this.$http = $http;
    }

    getResults() {
        if(this.filters) {
            return this.$http.post('/api/results', this.filters).then(response => response.data);
        }
        return this.$http.get('/api/results').then(response => response.data);
    }

    runTest(type) {
        return this.$http.get('/api/test?type=' + type);
    }

    setFilters(filters) {
        this.filters = filters;
    }

}
ResultsService.$inject = ['$http'];
export default ResultsService;