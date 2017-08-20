import resultsTableTemplate from './results-table.html'
"use strict"

class Ctrl {

    constructor() {
        this.query = {
            order: 'id',
            limit: 10,
            page: 1
        };
    }

}

export default {
    template: resultsTableTemplate,
    controller: Ctrl,
    controllerAs: 'ctrl',
    bindings: {
        results: '<'
    }
}