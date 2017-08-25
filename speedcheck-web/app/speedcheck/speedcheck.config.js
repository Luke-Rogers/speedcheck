export default function config($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('blue')
        .accentPalette('blue');
}
config.$inject = ['$mdThemingProvider'];