import resultsTableTemplate from './results-table.html'
"use strict"

class Ctrl {

    constructor(speedcheckService) {
        this.speedcheckService = speedcheckService;
        this.selected = [];
        this.query = {
            order: 'id',
            limit: 10,
            page: 1
        };
    }

    deleteRecord() {
        this.speedcheckService.deleteResults(this.selected).then(() => {
            this.refreshData();
        })
    }

}

Ctrl.$inject = ['speedcheckService'];
export default {
    template: resultsTableTemplate,
    controller: Ctrl,
    controllerAs: 'ctrl',
    bindings: {
        refreshData: "&",
        results: '<'
    }
}