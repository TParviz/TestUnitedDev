package tj.test.testuniteddev.domain.net

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tj.test.testuniteddev.data.models.MoviesData
import tj.test.testuniteddev.data.repositories.MoviesRepository
import javax.inject.Inject

interface GetMoviesUseCase : FlowUseCase<GetMoviesUseCaseParams, List<MoviesData>>

class GetMoviesUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetMoviesUseCase {
    override fun execute(param: GetMoviesUseCaseParams): Flow<Result<List<MoviesData>>> = flow {
        val result = moviesRepository.getMovies(
            search = param.search,
            page = param.page
        )
        emit(Result.success(result))
    }
}

data class GetMoviesUseCaseParams(val search: String, val page: Int)
