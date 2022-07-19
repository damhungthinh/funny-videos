import { User } from '@entity/user'

export type Movie = {
  id: number
  videoKey: string
  videoUrl: string
  active: boolean
  sharedBy: User
}

export type MovieConditions = {
  page: number
  size: number
}
