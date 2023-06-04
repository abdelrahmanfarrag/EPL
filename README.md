# EPL
EPL Project.

The Archticture used in the assessment is MVI :-

MVI is combination of MVP and an enhance form of MVVM , the contract is a great way to document what is exactly this screen or feature is doing by reading the events
which is emitted from Fragment to viewmodel or ViewModel to itself , and read the state that will help to know which attribute the view is using.

The Assessment is built on multi-module with 3 modules [ Data - Domain - Presentation ] following the clean architecture
Data depends on domain , presentation is depends on domain , while domain CAN NOT depend on anything.

Flow Illustraion :-

View sending an event to viewmodel requesting a data,then viewmodel render this event and sends it to usecase which is using the repository to fetch data from local 
or remote data source.

TDD :

This project had been written using Test-Driven development, and it tests<br>
1.ViewModels.<br>
2.UseCases.<br>
3.Repositories.<br>
4.RemoteDataSource.<br>

Technologies Used :-

Retrofit.<br>
Room.<br>
Hilt.<br>
Jetpack Navigation.<br>
Coroutines.<br>
Flow.<br>

