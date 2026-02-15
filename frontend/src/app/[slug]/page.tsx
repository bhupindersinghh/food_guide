'use client';

import { useQuery } from '@tanstack/react-query';
import { creatorApi, analyticsApi } from '@/lib/api';
import { useParams } from 'next/navigation';
import RecommendationCard from '@/components/RecommendationCard';
import { useState, useEffect } from 'react';

export default function CreatorPage() {
  const params = useParams();
  const slug = params.slug as string;
  const [searchQuery, setSearchQuery] = useState('');
  const [sessionId] = useState(() => crypto.randomUUID());

  const { data: creator, isLoading: creatorLoading } = useQuery({
    queryKey: ['creator', slug],
    queryFn: () => creatorApi.getBySlug(slug),
  });

  const { data: recommendations, isLoading: recommendationsLoading } = useQuery({
    queryKey: ['recommendations', slug],
    queryFn: () => creatorApi.getRecommendations(slug),
  });

  useEffect(() => {
    if (creator) {
      analyticsApi.trackEvent({
        eventType: 'PAGE_VIEW',
        creatorSlug: slug,
        sessionId,
      });
    }
  }, [creator, slug, sessionId]);

  const filteredRecommendations = recommendations?.filter((rec) => {
    if (!searchQuery) return true;
    const query = searchQuery.toLowerCase();
    return (
      rec.dishName.toLowerCase().includes(query) ||
      rec.restaurantName.toLowerCase().includes(query) ||
      rec.area.toLowerCase().includes(query)
    );
  });

  if (creatorLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-pink-500 mx-auto mb-4"></div>
          <p className="text-gray-600">Loading...</p>
        </div>
      </div>
    );
  }

  if (!creator) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-center">
          <h1 className="text-4xl font-bold text-gray-900 mb-4">Creator Not Found</h1>
          <p className="text-gray-600">This creator doesn't exist or has been removed.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Creator Header */}
      <div
        className="bg-instagram-gradient text-white py-12"
        style={{ backgroundColor: creator.themeColor || undefined }}
      >
        <div className="container mx-auto px-4 text-center">
          {creator.profileImageUrl ? (
            <img
              src={creator.profileImageUrl}
              alt={creator.displayName}
              className="w-32 h-32 rounded-full mx-auto mb-4 border-4 border-white shadow-lg object-cover"
            />
          ) : (
            <div className="w-32 h-32 rounded-full mx-auto mb-4 border-4 border-white shadow-lg bg-white/20 flex items-center justify-center text-6xl">
              👤
            </div>
          )}
          <h1 className="text-4xl font-bold mb-2">{creator.displayName}</h1>
          {creator.instagramHandle && (
            <a
              href={`https://instagram.com/${creator.instagramHandle}`}
              target="_blank"
              rel="noopener noreferrer"
              className="inline-flex items-center gap-2 text-lg hover:underline mb-4"
            >
              📷 @{creator.instagramHandle}
            </a>
          )}
          {creator.bio && (
            <p className="text-lg opacity-90 max-w-2xl mx-auto mt-4">{creator.bio}</p>
          )}
          <div className="flex gap-6 justify-center mt-6 text-sm">
            <div>
              <span className="font-bold text-2xl">{creator.totalRecommendations}</span>
              <p className="opacity-90">Recommendations</p>
            </div>
            <div>
              <span className="font-bold text-2xl">{creator.totalPageViews}</span>
              <p className="opacity-90">Page Views</p>
            </div>
          </div>
        </div>
      </div>

      {/* Search Section */}
      <div className="container mx-auto px-4 -mt-8 mb-8">
        <div className="bg-white rounded-full shadow-xl p-2 flex items-center max-w-2xl mx-auto">
          <span className="pl-4 text-gray-400 text-xl">🔍</span>
          <input
            type="text"
            placeholder="Search dishes, restaurants, or areas..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="flex-1 px-4 py-3 text-lg outline-none"
          />
        </div>
        {searchQuery && (
          <p className="text-center mt-3 text-gray-600 text-sm">
            Showing {filteredRecommendations?.length} recommendation
            {filteredRecommendations?.length !== 1 ? 's' : ''}
          </p>
        )}
      </div>

      {/* Recommendations Grid */}
      <div className="container mx-auto px-4 pb-16">
        {recommendationsLoading ? (
          <div className="text-center py-12">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-pink-500 mx-auto mb-4"></div>
            <p className="text-gray-600">Loading recommendations...</p>
          </div>
        ) : filteredRecommendations && filteredRecommendations.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {filteredRecommendations.map((recommendation) => (
              <RecommendationCard
                key={recommendation.id}
                recommendation={recommendation}
                onInstagramClick={() => {
                  analyticsApi.trackEvent({
                    eventType: 'INSTAGRAM_CLICK',
                    recommendationId: recommendation.id,
                    creatorSlug: slug,
                    sessionId,
                  });
                }}
                onMapsClick={() => {
                  analyticsApi.trackEvent({
                    eventType: 'MAPS_CLICK',
                    recommendationId: recommendation.id,
                    creatorSlug: slug,
                    sessionId,
                  });
                }}
              />
            ))}
          </div>
        ) : (
          <div className="text-center py-12">
            <p className="text-4xl mb-4">🤔</p>
            <p className="text-xl text-gray-600 mb-2">No recommendations found</p>
            {searchQuery && (
              <p className="text-gray-500">
                Try searching for "biryani", "momos", or "street food"
              </p>
            )}
          </div>
        )}
      </div>

      {/* Footer CTA */}
      <div className="bg-gray-100 py-8">
        <div className="container mx-auto px-4 text-center">
          <p className="text-gray-600 mb-2">Are you a creator?</p>
          <a
            href="/auth/register"
            className="text-pink-600 font-semibold hover:underline"
          >
            Create your page →
          </a>
        </div>
      </div>
    </div>
  );
}
