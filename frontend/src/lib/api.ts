import axios from 'axios';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1';

export const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add auth token to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Types
export interface Creator {
  username: string;
  displayName: string;
  bio: string;
  profileImageUrl: string | null;
  instagramHandle: string;
  slug: string;
  themeColor: string;
  totalRecommendations: number;
  totalPageViews: number;
}

export interface Recommendation {
  id: number;
  dishName: string;
  restaurantName: string;
  area: string;
  city: string;
  fullAddress: string | null;
  latitude: number | null;
  longitude: number | null;
  googleMapsUrl: string | null;
  creatorQuote: string | null;
  description: string | null;
  dishCategory: string | null;
  cuisineType: string | null;
  mealType: string | null;
  priceRange: string | null;
  tags: string[];
  viewCount: number;
  instagramClicks: number;
  mapsClicks: number;
  saveCount: number;
  thumbnailUrl: string | null;
  instagramUrl: string | null;
  publishedAt: string;
  createdAt: string;
}

export interface ApiResponse<T> {
  success: boolean;
  message?: string;
  data: T;
  timestamp: string;
}

// API Functions
export const creatorApi = {
  getBySlug: async (slug: string) => {
    const { data } = await api.get<ApiResponse<Creator>>(`/public/creators/${slug}`);
    return data.data;
  },

  getRecommendations: async (slug: string, page = 0, size = 20) => {
    const { data } = await api.get<ApiResponse<Recommendation[]>>(
      `/public/creators/${slug}/recommendations`,
      { params: { page, size } }
    );
    return data.data;
  },
};

export const authApi = {
  register: async (userData: {
    email: string;
    password: string;
    displayName: string;
    slug: string;
    instagramHandle?: string;
    bio?: string;
  }) => {
    const { data } = await api.post('/auth/register', userData);
    return data.data;
  },

  login: async (credentials: { email: string; password: string }) => {
    const { data } = await api.post('/auth/login', credentials);
    return data.data;
  },
};

export const recommendationApi = {
  create: async (recommendation: {
    dishName: string;
    restaurantName: string;
    area?: string;
    fullAddress?: string;
    googleMapsUrl?: string;
    creatorQuote?: string;
    description?: string;
    dishCategory?: string;
    cuisineType?: string;
    mealType?: string;
    priceRange?: string;
    tags?: string[];
  }) => {
    const { data } = await api.post('/creator/recommendations', recommendation);
    return data.data;
  },

  getMyRecommendations: async (page = 0, size = 20) => {
    const { data } = await api.get('/creator/recommendations', {
      params: { page, size },
    });
    return data.data;
  },
};

export const analyticsApi = {
  trackEvent: async (event: {
    eventType: string;
    recommendationId?: number;
    creatorSlug?: string;
    searchQuery?: string;
    sessionId?: string;
  }) => {
    try {
      await api.post('/public/events', event);
    } catch (error) {
      // Don't throw error - analytics failures shouldn't break the app
      console.warn('Analytics tracking failed:', error);
    }
  },
};
