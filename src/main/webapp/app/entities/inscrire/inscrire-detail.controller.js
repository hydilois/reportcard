(function() {
    'use strict';

    angular
        .module('reportcardApp')
        .controller('InscrireDetailController', InscrireDetailController);

    InscrireDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Inscrire', 'Eleve', 'Classe'];

    function InscrireDetailController($scope, $rootScope, $stateParams, previousState, entity, Inscrire, Eleve, Classe) {
        var vm = this;

        vm.inscrire = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('reportcardApp:inscrireUpdate', function(event, result) {
            vm.inscrire = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
